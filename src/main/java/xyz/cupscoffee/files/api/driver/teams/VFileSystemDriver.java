package xyz.cupscoffee.files.api.driver.teams;

import com.google.gson.*;
import com.google.gson.stream.*;
import xyz.cupscoffee.files.api.*;
import xyz.cupscoffee.files.api.File;
import xyz.cupscoffee.files.api.driver.SavDriver;
import xyz.cupscoffee.files.api.exception.InvalidFormatFileException;
import xyz.cupscoffee.files.api.implementation.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class VFileSystemDriver implements SavDriver {
    @Override
    public SavStructure readSavFile(InputStream inputStream) throws InvalidFormatFileException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Disk.class, new SimpleDiskDeserializer())
                .registerTypeAdapter(File.class, new SimpleFileDeserializer())
                .registerTypeAdapter(Folder.class, new SimpleFolderDeserializer())
                .registerTypeAdapter(Metadata.class, new SimpleMetadataDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .registerTypeAdapter(ByteBuffer.class, new ByteBufferDeserializer())
                .registerTypeAdapter(Path.class, new PathAdapter())
                .setPrettyPrinting().create();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));

            reader.readLine(); //ignore header

            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }

            reader.close();

            String jsonString = contentBuilder.toString().trim();

            return gson.fromJson(jsonString, SimpleSavStructure.class);

        } catch (IOException e) {
            throw new InvalidFormatFileException("Error reading file");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw new InvalidFormatFileException("Error reading file");
            }
        }
    }

    private static class SimpleDiskDeserializer implements JsonDeserializer<SimpleDisk> {

        @Override
        public SimpleDisk deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            Folder rootFolder = context.deserialize(jsonObject.get("rootFolder"), SimpleFolder.class);
            long limitSize = jsonObject.get("limitSize").getAsLong();
            Map<String, String> metadata = context.deserialize(jsonObject.get("metadata"), Map.class);
            return new SimpleDisk(name, rootFolder, limitSize, metadata);
        }
    }

    private static class SimpleFolderDeserializer implements JsonDeserializer<SimpleFolder> {

        @Override
        public SimpleFolder deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();

            List<File> files = new ArrayList<>();
            JsonArray filesArray = jsonObject.getAsJsonArray("files");
            for (JsonElement fileElement : filesArray) {
                SimpleFile file = context.deserialize(fileElement, SimpleFile.class);
                files.add(file);
            }

            List<Folder> folders = new ArrayList<>();
            JsonArray foldersArray = jsonObject.getAsJsonArray("folders");
            for (JsonElement folderElement : foldersArray) {
                SimpleFolder folder = context.deserialize(folderElement, SimpleFolder.class);
                folders.add(folder);
            }

            LocalDateTime createdDateTime = context.deserialize(jsonObject.get("createdDateTime"), LocalDateTime.class);
            LocalDateTime lastModifiedDateTime = context.deserialize(jsonObject.get("lastModifiedDateTime"), LocalDateTime.class);
            Path path = context.deserialize(jsonObject.get("path"), Path.class);
            Map<String, String> otherMeta = context.deserialize(jsonObject.get("otherMeta"), Map.class);
            return new SimpleFolder(name, files, folders, createdDateTime, lastModifiedDateTime, path, otherMeta);
        }

    }

    private static class SimpleFileDeserializer implements JsonDeserializer<SimpleFile> {

        @Override
        public SimpleFile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            ByteBuffer content = context.deserialize(jsonObject.get("content"), ByteBuffer.class);
            LocalDateTime createdDateTime = context.deserialize(jsonObject.get("createdDateTime"), LocalDateTime.class);
            LocalDateTime lastModifiedDateTime = context.deserialize(jsonObject.get("lastModifiedDateTime"), LocalDateTime.class);
            Path path = context.deserialize(jsonObject.get("path"), Path.class);
            Map<String, String> otherMeta = context.deserialize(jsonObject.get("otherMeta"), Map.class);
            return new SimpleFile(name, content, createdDateTime, lastModifiedDateTime, path, otherMeta);
        }
    }

    private static class SimpleMetadataDeserializer implements JsonDeserializer<SimpleMetadata> {
        @Override
        public SimpleMetadata deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            LocalDateTime createdDateTime = LocalDateTime.parse(jsonObject.get("createdDateTime").getAsString());
            LocalDateTime lastModifiedDateTime = LocalDateTime.parse(jsonObject.get("lastModifiedDateTime").getAsString());
            long size = jsonObject.get("size").getAsLong();
            Path path = Paths.get(jsonObject.get("path").getAsString());
            Map<String, String> otherMeta = context.deserialize(jsonObject.get("otherMeta"), Map.class);
            return new SimpleMetadata(createdDateTime, lastModifiedDateTime, size, path, otherMeta);
        }
    }

    private static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString());
        }
    }

    private static class ByteBufferDeserializer extends TypeAdapter<ByteBuffer> {
        @Override
        public ByteBuffer read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            byte[] bytes = Base64.getDecoder().decode(reader.nextString());
            return ByteBuffer.wrap(bytes);
        }

        @Override
        public void write(JsonWriter writer, ByteBuffer data) throws IOException {
            if (data == null) {
                writer.nullValue();
                return;
            }
            ByteBuffer base64Bytes = Base64.getEncoder().encode(data.duplicate());
            String base64String = StandardCharsets.ISO_8859_1.decode(base64Bytes).toString();
            writer.value(base64String);
        }
    }

    private static class PathAdapter implements JsonDeserializer<Path> {
        @Override
        public Path deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return Paths.get(json.getAsString());
            } catch (Exception e) {
                throw new JsonParseException("Error al deserializar la ruta: " + json.getAsString(), e);
            }
        }
    }
}
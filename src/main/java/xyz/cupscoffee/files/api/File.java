package xyz.cupscoffee.files.api;

import java.nio.ByteBuffer;

public interface File extends Metadata {
    String getName();

    ByteBuffer getContent();
}

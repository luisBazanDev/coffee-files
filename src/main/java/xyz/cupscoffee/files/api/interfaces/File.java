package xyz.cupscoffee.files.api.interfaces;

import java.nio.ByteBuffer;

public interface File extends Metadata {
    String getName();

    ByteBuffer getContent();
}

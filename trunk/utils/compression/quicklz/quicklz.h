unsigned int qlz_compress(const void *source, char *destination, unsigned int size);
unsigned int qlz_decompress(const char *source, void *destination);
unsigned int qlz_size_decompressed(const char *source);
unsigned int qlz_size_compressed(const char *source);
unsigned int qlz_compress_packet(const void *source, char *destination, unsigned int size, char *buffer);
unsigned int qlz_decompress_packet(const char *source, void *destination, char *buffer);
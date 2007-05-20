function size = getFileSize(fileName)
fid = fopen(fileName);
fseek(fid,0,1);
size = ftell(fid);
fclose(fid);
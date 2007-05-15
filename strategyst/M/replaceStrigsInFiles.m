function replaceStringsInFiles(inputDir,outputDir,findStr,replaceStr)
files = ls(dir);
for i=3:size(files,1)
    i
    file = files(i,:);
    fid = fopen([dir file],'r');
    input = fread(fid,'uint8=>char')';
    fclose(fid);
    output = strrep(input, findStr, replsceStr);
    fid = fopen([dirOut file],'w');
    fwrite(fid,output');
    fclose(fid);
end
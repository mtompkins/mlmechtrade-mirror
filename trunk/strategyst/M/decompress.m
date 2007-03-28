function sparseArray = decompress(data,range)
S = sparse(min(range):max(range),1);
for i = range,
    compressionInfo = d.compressionMap.get(i);
    chunk = compressionInfo(1);
    indicesCompressed = compressionInfo(2:end);
    for j = indicesCompressed
        
        
        TODO
end % end for

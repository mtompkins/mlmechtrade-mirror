function rez = getSymbolData(data, index)
if (data.compressionMap.containsKey(index)),
    compressedSymbols = data.compressionMap.get(index);
    chunk = compressedSymbols(1);
    compressedSymbols(1) = [];
    createGlobalCashe();
    rez = lookupFromCache(index);
    if (isempty(rez)),
        comressed = data.compressedData{chunk};
        uncompressed = dunzip(comressed);
        startIdx = 1;
        for key = compressedSymbols,
            len = data.marketData(key).seriesLen;
            value = uncompressed(startIdx:len,:);
            if (key == index),
                rez = value;
                addToCache(key, value);
            end % if
            clear uncompressed;
        end % for
    end % isempty
else % containsKey
    rez = data.marketData(i);
end % containsKey

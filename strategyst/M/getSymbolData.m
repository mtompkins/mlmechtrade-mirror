function rez = getSymbolData(data, index)
if (data.compressionMap.containsKey(index)),
    compressedSymbols = data.compressionMap.get(index);
    chunk = compressedSymbols(1);
    compressedSymbols(1) = [];
    createGlobalCashe();
    rez = lookupFromCache(i);
    if (isempty(rez)),
        comressed = data.compressedData{chunk};
%        try
            uncompressed = dunzip(comressed);
%         catch
%             casheManager = evalin('base', 'TS_CASHE_MANAGER');
%             cache = casheManager.getCache('finTmSer');
%             cache.dispose();
%             uncompressed = dunzip(comressed);
%         end
        startIdx = 1;
        for key = compressedSymbols,
            len = data.marketData(key).seriesLen;
            value = uncompressed(startIdx:len,:);
%             element = net.sf.ehcache.Element(key,value);
%             casheManager = evalin('base', 'TS_CASHE_MANAGER');
%             cache = casheManager.getCache('finTmSer');
%             cache.put(element);
            if (key == index),
                rez = value;
                    casheManager.index = i;
                    casheManager.data = value;
            end % if
            clear uncompressed;
        end % for
    end % isempty
else % containsKey
    rez = data.marketData(i);
end % containsKey

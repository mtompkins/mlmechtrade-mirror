function result = cutMinCountBars(data, minBars)
result.compressionMap = java.util.HashMap();
resIdx = 0;
result.filter = data.filter;
for i = 1:size(data.marketData,2)
    symbol = data.marketData(i);
    if (~isempty(symbol.seriesLen) && (minBars <= symbol.seriesLen))
        resIdx = resIdx + 1;
        result.marketData(resIdx) = symbol;
    end
end
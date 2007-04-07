function result = cutMinCountBars(data, minBars)
result.compressionMap = java.util.HashMap();
resIdx = 0;
for i = 1:size(data.marketData,2)
    symbol = data.marketData(i);
    if (minBars < symbol.seriesLen)
        resIdx = resIdx + 1;
        result.marketData(i) = symbol;
    end
end
function result = cutDateInterval(data, date1, date2)
d1 = datenum(date1,'yyyymmdd');
d2 = datenum(date2,'yyyymmdd');
data.compressionMap = java.util.HashMap();
data.compressedData = {};
result.filter = data.filter;
resIdx = 0;
for i = 1:size(data.marketData,2)
    symbol = data.marketData(i);
    idx = find(symbol.time >= d1 & symbol.time <= d2);
    if (idx)
        resIdx = resIdx + 1;
        result.marketData(i).symbol = symbol.symbol;
        result.marketData(i).seriesLen = length(idx);
        result.marketData(i).fields = symbol.fields;
        result.marketData(i).time = symbol.time(idx);
        result.marketData(i).data = symbol.data(idx,:);
    end
end
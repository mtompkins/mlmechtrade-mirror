function result = cutDateInterval(data, d1, d2, index)
% data - quotes data structure
% d1 - timestamp from. Keep data only time >= d1
% d2 - timestamp to. Keep data only time <= d2
% index - the index of symbol to be processed

if nargin < 4
    index = 1:size(data.marketData,2);
end

for i = index
    symbol = data.marketData(i);
    idx = find(symbol.time >= d1 & symbol.time <= d2);
    if (idx)
        result.marketData(i).symbol = symbol.symbol;
        result.marketData(i).seriesLen = length(idx);
        result.marketData(i).fields = symbol.fields;
        result.marketData(i).time = symbol.time(idx);
        result.marketData(i).data = symbol.data(idx,:);
    end
end
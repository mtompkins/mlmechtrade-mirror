function high = getHigh(data, index)
% Returns Open series
high =  data.marketData(index).data(:,3);
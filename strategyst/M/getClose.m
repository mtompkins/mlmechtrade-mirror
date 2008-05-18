function close = getClose(data, index)
% Returns Close series
close =  data.marketData(index).data(:,4);
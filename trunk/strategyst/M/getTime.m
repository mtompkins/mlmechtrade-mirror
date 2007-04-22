function time = getTime(data, index)
% Returns time series

rez = getSymbolData(data, index);
time =  rez(:,1);
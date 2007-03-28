function open = getClose(data, index)
% Returns Open series

rez = getSymbolData(data, index);
open =  rez(:,5);
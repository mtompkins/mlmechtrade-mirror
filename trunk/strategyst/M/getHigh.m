function open = getHigh(data, index)
% Returns Open series

rez = getSymbolData(data, index);
open =  rez(:,3);
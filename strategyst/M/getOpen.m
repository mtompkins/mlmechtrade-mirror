function open = getOpen(data, index)
% Returns Open series

rez = getSymbolData(data, index);
open =  rez(:,2);
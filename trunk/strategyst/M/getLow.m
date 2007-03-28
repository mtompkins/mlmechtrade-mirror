function open = getLow(data, index)
% Returns Open series

rez = getSymbolData(data, index);
open =  rez(:,4);
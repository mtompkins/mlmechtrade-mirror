function open = getTime(data, index)
% Returns Open series

rez = getSymbolData(data, index);
open =  rez(:,1);
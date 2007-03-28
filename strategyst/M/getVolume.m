function open = getVolume(data, index)
% Returns Open series

rez = getSymbolData(data, index);
open =  rez(:,6);
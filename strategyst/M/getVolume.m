function volume = getVolume(data, index)
% Returns Open series

rez = getSymbolData(data, index);
volume =  rez(:,6);
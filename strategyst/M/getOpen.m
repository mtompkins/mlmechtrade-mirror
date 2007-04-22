function open = getOpen(data, index)
% Returns Open series

rez = getSymbolData(data, index);
open =  rez(:,2);
if (data.filter == 1)
    open(2:end) = open(2:end) + open(1);
end
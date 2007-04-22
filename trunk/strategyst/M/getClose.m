function close = getClose(data, index)
% Returns Close series

rez = getSymbolData(data, index);
close =  rez(:,5);
if (data.filter == 1)
    close(2:end) = close(2:end) + close(1);
end
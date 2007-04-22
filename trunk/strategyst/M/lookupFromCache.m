function data = lookupFromCache(key)
casheManager = evalin('base', 'TS_CASHE_MANAGER');
%data = casheManager.get(key);
if (casheManager.key == key)
    data = casheManager.value;
else
    data = [];
end


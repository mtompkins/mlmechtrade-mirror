function addToCache(key,value)
casheManager = evalin('base', 'TS_CASHE_MANAGER');
%casheManager.put(key,value);
casheManager.key = key;
casheManager.value = value;
assignin('base', 'TS_CASHE_MANAGER', casheManager)

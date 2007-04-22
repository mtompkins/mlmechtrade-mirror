function addToCache(key,value)
casheManager = evalin('base', 'TS_CASHE_MANAGER');
casheManager.put(key,value);

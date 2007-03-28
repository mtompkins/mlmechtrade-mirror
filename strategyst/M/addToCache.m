function addToCache(element)
casheManager = evalin('base', 'TS_CASHE_MANAGER');
cache = casheManager.getCache('finTmSer');
cache.put(element);
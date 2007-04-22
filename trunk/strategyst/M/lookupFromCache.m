function data = lookupFromCache(key)
casheManager = evalin('base', 'TS_CASHE_MANAGER');
data = casheManager.get(key);


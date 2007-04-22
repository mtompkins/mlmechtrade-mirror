function createGlobalCashe()
try
    casheManager = evalin('base', 'TS_CASHE_MANAGER');
catch
    casheManager = java.util.WeakHashMap();
    assignin('base','TS_CASHE_MANAGER',casheManager);
end

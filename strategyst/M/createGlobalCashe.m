function createGlobalCashe()
    %casheManager = java.util.WeakHashMap();
    %assignin('base','TS_CASHE_MANAGER',casheManager);
    casheManager.key = [];
    casheManager.value = [];
    assignin('base', 'TS_CASHE_MANAGER', casheManager)    
end

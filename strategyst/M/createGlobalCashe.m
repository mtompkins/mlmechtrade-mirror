function createGlobalCashe()
%import net.sf.ehcache.*;
%import java.io.*;
try
    casheManager = evalin('base', 'TS_CASHE_MANAGER');
catch
    casheManager = {};
end
%if (~isjava(casheManager)),
%     casheManager = net.sf.ehcache.CacheManager();
%     memoryOnlyCache = net.sf.ehcache.Cache('finTmSer', 10, false, false, 60, 2);
%     casheManager.addCache(memoryOnlyCache);
    assignin('base','TS_CASHE_MANAGER',casheManager);
%end
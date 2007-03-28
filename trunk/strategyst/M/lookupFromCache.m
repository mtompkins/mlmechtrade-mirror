function data = lookupFromCache(i)
casheManager = evalin('base', 'TS_CASHE_MANAGER');
if (isfield(casheManager,'index'))
    data = casheManager.data;
else
    data = [];
end
% cache = casheManager.getCache('finTmSer');
% element = cache.get(i);
% data = [];
% if (~isempty(element))
%     data = element.getValue();
% end

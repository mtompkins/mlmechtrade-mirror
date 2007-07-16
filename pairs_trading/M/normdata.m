% DESCRIPTION:
% 
%   This function is part of pairstrading.m function. The basic objective
%   of the present function is to normalized the data based on it's mean
%   and standard deviation.
%
%   USAGE: [New_x]=normdata(x)
%
%            INPUT:
%                   x - A matrix with the data to be normalized.
%
%            OUTPUT:
%                   New_x - A matrix with the same size as x, with the
%                   normalized data.
%
%
%   Author: Marcelo Scherer Perlin
%   Email:  marceloperlin@gmail.com
%   University of Rio Grande do Sul/ Brazil
%   Department of Finance
%   Created: November/2006
%   Last Update: -

function [New_x]=normdata(x)

[n2]=size(x,2);

for i=1:n2
    mean_x=mean(x(:,i));
    std_x=std(x(:,i));

    New_x(:,i)=(x(:,i)-mean_x)/std_x;

end
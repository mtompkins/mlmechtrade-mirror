% DESCRIPTION:
% 
%   This file verifies how many transaction were really made with the long
%   and short postions at the matrix x. Suppose you've bought a stock
%   yesterday and you have an order to buy it again today. This case has only
%   one transaction cost since the stock was already in the portfolio in t-1, so
%   there is no need to buy it again.
%
%   USAGE: [n]=trades(x)
%
%            INPUT:
%                   x - A matrix with long or short positions, values 1 and -1 respectively. 
%
%            OUTPUT:
%                   n - A matrix with the same size as x, with all transactions made.
%
%
%   Author: Marcelo Scherer Perlin
%   Email:  marceloperlin@gmail.com
%   University of Rio Grande do Sul/ Brazil
%   Department of Finance
%   Created: November/2006
%   Last Update: -

function [n]=trades(x)

[n1,n2]=size(x);

n=zeros(n1,n2);
x=abs(x);

for j=1:n2
    for i=1:n1-1
        if x(i,j)*x(i+1,j)==0&&abs(x(i+1,j))==1
            n(i+1,j)=1;
        end
    end
end
    
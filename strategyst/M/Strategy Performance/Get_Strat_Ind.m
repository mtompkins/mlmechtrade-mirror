function [Out]=Get_Strat_Ind(Weight_Matrix,Ret_Matrix,Mkt_Ret_Vec)

    Ret_Vec=sum(Weight_Matrix.*Ret_Matrix,2);

    Out.Sharpe_Value=Get_Sharpe(Ret_Vec);
    [Out.Max_DD,Out.Ulcer_idx]=Get_DD_Ulcer(Ret_Vec);
    [Out.Beta_Value,Out.Alpha_Value]=Get_Beta(Ret_Vec,Mkt_Ret_Vec);
    [Out.Raw_Ret,Out.Exc_Ret]=Get_Returns(Weight_Matrix,Ret_Matrix);
    
    
function [Sharpe_Value]=Get_Sharpe(Ret_Vec)

    Sharpe_Value=mean(Ret_Vec)*248/(std(Ret_Vec)*sqrt(248));

function [Max_DD,Ulcer_idx]=Get_DD_Ulcer(Ret_Vec)


    Price_Vec=ret2price(Ret_Vec,1); % Geting price series for continous return (log returns)

    [nr]=size(Price_Vec,1);

    SumSq = 0;
    MaxValue = 0;

    for T = 1:nr

        if Price_Vec(T) > MaxValue
            MaxValue= Price_Vec(T);
        else
            SumSq(T,1) = (100*((Price_Vec(T)/MaxValue)- 1)).^2;
            Sq(T,1)=(Price_Vec(T)/MaxValue- 1)*100;

        end


    end

    Ulcer_idx=sqrt(sum(SumSq)/nr);
    Max_DD=min(Sq);

function [Beta_Value,Alpha_Value]=Get_Beta(Ret_Vec,Mkt_Ret_Vec)

    [nr]=size(Ret_Vec,1);

    param=regress(Ret_Vec,[ones(nr,1),Mkt_Ret_Vec]);

    Beta_Value=param(2);
    Alpha_Value=param(1);

function [Raw_Ret,Exc_Ret]=Get_Returns(Weight_Matrix,Ret_Matrix)

    [nr]=size(Weight_Matrix,1);

    Raw_Ret=sum(sum(Weight_Matrix.*Ret_Matrix));

    Bench_Port=sum( sum(Ret_Matrix).*(sum(Weight_Matrix~=0)./nr) );

    Exc_Ret=Raw_Ret-Bench_Port;




#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>

/*
 * TA_SMA - Simple Moving Average
 * 
 * Input  = double
 * Output = double
 * 
 * Optional Parameters
 * -------------------
 * optInTimePeriod:(From 2 to 100000)
 *    Number of period
 * 
 * 
 */
TA_RetCode TA_SMA( int    startIdx,
                   int    endIdx,
                   const double inReal[],
                   int           optInTimePeriod, /* From 2 to 100000 */
                   int          *outBegIdx,
                   int          *outNbElement,
                   double        outReal[] );


/* The gateway routine */
void mexFunction(int nlhs, mxArray *plhs[],
                 int nrhs, const mxArray *prhs[])
{
/* ----------------- Variables ----------------- */
/* input variables */
  int startIdx;
  int endIdx;
  double *inReal;
  int optInTimePeriod;  
/* output variables */ 
  int outBegIdx;
  int outNbElement;
  double *outReal;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs != 1 && nrhs != 2) mexErrMsgTxt("2 inputs possible 1 optional.");
  if (nlhs != 1) mexErrMsgTxt("1 output required.");
  
/* ----------------- INPUT ----------------- */  
/* Process inReal */
  /* Create a pointer to the input matrix inSeries. */
  inReal = mxGetPr(prhs[0]);
  
  /* Get the dimensions of the matrix input inHigh. */
  inSeriesRows = mxGetM(prhs[0]);
  inSeriesCols = mxGetN(prhs[0]);
  startIdx = 0;
  endIdx = inSeriesRows - 1;
  if (inSeriesCols != 1) mexErrMsgTxt("inHigh only vector alowed.");
  
  mexPrintf("%s%i\n", "inSeriesRows=", inSeriesRows);
 /* Process optInTimePeriod */ 
  if (nrhs == 2) {
	if (!mxIsDouble(prhs[1]) || mxIsComplex(prhs[1]) ||
      mxGetN(prhs[1])*mxGetM(prhs[1]) != 1) 
    	mexErrMsgTxt("Input optInTimePeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInTimePeriod = (int) mxGetScalar(prhs[1]);
  } else {
  	optInTimePeriod = 30;
  }

/* ----------------- OUTPUT ----------------- */
   outReal = mxCalloc(inSeriesRows, sizeof(double));    
/* -------------- Invocation ---------------- */
   retCode = TA_SMA(startIdx, endIdx,
                    inReal,
                    optInTimePeriod, 
                    &outBegIdx, &outNbElement, outReal);
   if (retCode) {
   	   mxFree(outReal);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
   // Populate Output
   mexPrintf("%s%i %s%i","outBegIdx=",outBegIdx,"outNbElement=",outNbElement);
   plhs[0] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
   memcpy(mxGetData(plhs[0]), outReal, (outBegIdx+outNbElement)*mxGetElementSize(plhs[0]));
   mxFree(outReal);  
} /* END mexFunction */

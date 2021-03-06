#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_CDLMORNINGSTAR - Morning Star
 * Input Parameters
 * -------------------
 * open
 * high
 * low
 * close
 * 
 * Optional Parameters
 * -------------------
 * optInPenetration:(From 0.000000e+0 to 3.000000e+37) Description: Percentage of penetration of a candle within another candle
 *
 * Output
 * -------------------
 * outInteger
 * 
 */
 
/* The gateway routine */
void mexFunction(int nlhs, mxArray *plhs[],
                 int nrhs, const mxArray *prhs[])
{
/* ----------------- Variables ----------------- */
/* input variables */
/* mandatory input */
  int startIdx;
  int endIdx;
  double * open;
  double * high;
  double * low;
  double * close;
/* optional input */
  double	 optInPenetration;
/* output variables */ 
  int outBegIdx;
  int outNbElement;
  int*	 outInteger;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 4 || nrhs > 5) mexErrMsgTxt("#5 inputs possible #1 optional.");
  if (nlhs != 1) mexErrMsgTxt("#1 output required.");
/* ----------------- INPUT ----------------- */ 
  /* Create a pointer to the input matrix open. */
  open = mxGetPr(prhs[0]);
  /* Get the dimensions of the matrix input open. */
  inSeriesCols = mxGetN(prhs[0]);
  if (inSeriesCols != 1) mexErrMsgTxt("open only vector alowed.");
  /* Create a pointer to the input matrix high. */
  high = mxGetPr(prhs[1]);
  /* Get the dimensions of the matrix input high. */
  inSeriesCols = mxGetN(prhs[1]);
  if (inSeriesCols != 1) mexErrMsgTxt("high only vector alowed.");
  /* Create a pointer to the input matrix low. */
  low = mxGetPr(prhs[2]);
  /* Get the dimensions of the matrix input low. */
  inSeriesCols = mxGetN(prhs[2]);
  if (inSeriesCols != 1) mexErrMsgTxt("low only vector alowed.");
  /* Create a pointer to the input matrix close. */
  close = mxGetPr(prhs[3]);
  /* Get the dimensions of the matrix input close. */
  inSeriesCols = mxGetN(prhs[3]);
  if (inSeriesCols != 1) mexErrMsgTxt("close only vector alowed.");
  inSeriesRows = mxGetM(prhs[3]);  
  endIdx = inSeriesRows - 1;  
  startIdx = 0;

 /* Process optional arguments */ 
  if (nrhs >= 4+1) {
	if (!mxIsDouble(prhs[4]) || mxIsComplex(prhs[4]) ||
      mxGetN(prhs[4])*mxGetM(prhs[4]) != 1) 
    	mexErrMsgTxt("Input optInPenetration must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInPenetration =   mxGetScalar(prhs[4]);
  } else {
  	optInPenetration = 3.000000e-1;
  }

/* ----------------- OUTPUT ----------------- */
  outInteger = mxCalloc(inSeriesRows, sizeof(int));
/* -------------- Invocation ---------------- */

	retCode = TA_CDLMORNINGSTAR(
                   startIdx, endIdx,
                   open,
                   high,
                   low,
                   close,
                   optInPenetration,
                   &outBegIdx, &outNbElement,
                   outInteger);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outInteger);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateNumericMatrix(outBegIdx+outNbElement,1, mxINT32_CLASS, mxREAL);
  memcpy(((int *) mxGetData(plhs[0]))+outBegIdx, outInteger, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outInteger);  
} /* END mexFunction */

#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>

/*
 * TA_ATR - Average True Range 
 *  
 * Input  = High, Low, Close
 * Output = double
 * 
 * Optional Parameters
 * -------------------
 * optInTimePeriod:(From 1 to 100000)
 *    Number of period
 * 
 * 
 */
TA_RetCode TA_ATR( int    startIdx,
                   int    endIdx,
                   const double inHigh[],
                   const double inLow[],
                   const double inClose[],
                   int           optInTimePeriod, /* From 1 to 100000 */
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
  double *inHigh;
  double *inLow;
  double *inClose;
  int optInTimePeriod;  
/* output variables */ 
  int *outBegIdx;
  int *outNbElement;
  double *outReal;
/* input dimentions */ 
  int inHighRows;
  int inHighCols;
  int inLowRows;
  int inLowCols;
  int inCloseRows;
  int inCloseCols;
/* scalar arrays */
  const mwSize DIM = 1;
  const mwSize DIMS[] = {1};
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs != 5 && nrhs != 6) mexErrMsgTxt("6 inputs possible 1 optional.");
  if (nlhs != 3) mexErrMsgTxt("3 output required.");
  
/* ----------------- INPUT ----------------- */  
/* Validate startIdx */
    if (!mxIsDouble(prhs[0]) || mxIsComplex(prhs[0]) ||
      mxGetN(prhs[0])*mxGetM(prhs[0]) != 1) 
    mexErrMsgTxt("Input startIdx must be a scalar.");
   
   /* Get the scalar input x. */
   startIdx = (int) mxGetScalar(prhs[0]);
  
/* Validate endIdx */
    if (!mxIsDouble(prhs[1]) || mxIsComplex(prhs[1]) ||
      mxGetN(prhs[1])*mxGetM(prhs[1]) != 1) 
    mexErrMsgTxt("Input optInTimePeriod must be a scalar.");
   
   /* Get the scalar input endIdx. */
   endIdx = (int) mxGetScalar(prhs[1]);
   
/* Validate inHigh */
  /* Create a pointer to the input matrix inHigh. */
  inHigh = mxGetPr(prhs[2]);
  
  /* Get the dimensions of the matrix input inHigh. */
  inHighRows = mxGetM(prhs[2]);
  inHighCols = mxGetN(prhs[2]);
  
if (inHighCols != 1) mexErrMsgTxt("inHigh only vector alowed.");
  
  mexPrintf("%s%i\n", "inHighRows=", inHighRows);
  
/* Validate inLow */  
   /* Create a pointer to the input matrix inLow. */
  inHigh = mxGetPr(prhs[3]);
  
  /* Get the dimensions of the matrix input inHigh. */
  inLowRows = mxGetM(prhs[3]);
  inLowCols = mxGetN(prhs[3]);
  
  mexPrintf("%s%i\n", "inLowRows=", inLowRows);
  
if (inLowCols != 1) mexErrMsgTxt("inLow only vector alowed.");

/* Validate inClose */  
   /* Create a pointer to the input matrix inLow. */
  inClose = mxGetPr(prhs[4]);
  
  /* Get the dimensions of the matrix input inHigh. */
  inCloseRows = mxGetM(prhs[4]);
  inCloseCols = mxGetN(prhs[4]);
  
  mexPrintf("%s%i\n", "inCloseRows=", inCloseRows);
  
if (inCloseCols != 1) mexErrMsgTxt("inClose only vector alowed.");

/* Validate vector size */
  if (inHighRows != inLowRows || inLowRows != inCloseRows) 
      mexErrMsgTxt("Vectors must be euqal size.");

/* Optional parameter */
  if (nrhs == 6) {
    /* Check to make sure the input argument is a scalar. */
    if (!mxIsDouble(prhs[5]) || mxIsComplex(prhs[5]) ||
      mxGetN(prhs[5])*mxGetM(prhs[5]) != 1)
        mexErrMsgTxt("Input optInTimePeriod must be a scalar.");
       /* Get the scalar input x. */
        optInTimePeriod = mxGetScalar(prhs[0]);
 } else if (nrhs == 5) {
    optInTimePeriod = 15;
 } else {
      mexErrMsgTxt("Invalid number of parameters.");
 }
 
 mexPrintf("%s%i\n", "optInTimePeriod=", optInTimePeriod);

/* ----------------- OUTPUT ----------------- */
   plhs[0] = mxCreateNumericArray(DIM,DIMS,mxINT32_CLASS, mxREAL);
   outBegIdx = (int *) mxGetData(plhs[0]);
   
   plhs[1] = mxCreateNumericArray(DIM,DIMS,mxINT32_CLASS, mxREAL);
   outNbElement = (int *) mxGetData(plhs[1]);   

   plhs[2] = mxCreateDoubleMatrix(inHighRows,inHighCols, mxREAL);
   outReal = mxGetPr(plhs[2]);    
/* -------------- FIX OUTPUT ---------------- */
   retCode = TA_ATR(startIdx, endIdx,
                    inHigh, inLow, inClose,
                    optInTimePeriod, 
                    outBegIdx, outNbElement, outReal);
   if (retCode) {
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
} /* END mexFunction */

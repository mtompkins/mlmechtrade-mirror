#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>

/* The gateway routine */
void mexFunction(int nlhs, mxArray *plhs[],
                 int nrhs, const mxArray *prhs[])
{
  TA_RetCode retCode;
  retCode = TA_Initialize( );
  
  // Check for errors  
  if( retCode != TA_SUCCESS ){
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error - Unable to initialize TA_Lib!");
  } /* END IF ERROR */
} /* END mexFunction */
/*
Copyright  © Trustonic Limited 2013

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

  1. Redistributions of source code must retain the above copyright notice, this 
     list of conditions and the following disclaimer.

  2. Redistributions in binary form must reproduce the above copyright notice, 
     this list of conditions and the following disclaimer in the documentation 
     and/or other materials provided with the distribution.

  3. Neither the name of the Trustonic Limited nor the names of its contributors 
     may be used to endorse or promote products derived from this software 
     without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.gd.mobicore.pa.service.Test;

import java.util.Arrays;
import android.util.Log;
import com.gd.mobicore.pa.ifc.CommandResult;
import com.gd.mobicore.pa.ifc.CmpMsg;
import com.gd.mobicore.pa.ifc.CmpCommand;
import com.gd.mobicore.pa.ifc.CmpResponse;

public class CmpRootContRegisterActivate extends CmpTest{
        
    protected final static int CMD_ROOTID_INDEX=CMD_HEADER_LENGTH;
    protected final static int ROOTID_LENGTH=4;
    protected final static int CMD_ENC_INDEX=CMD_ROOTID_INDEX+ROOTID_LENGTH+SIZEFIELD_LENGTH;
  
    protected final static int CMD_MAC_INDEX=CMD_ENC_INDEX + ENC.length;

    protected final static int RSP_ENC_INDEX=RSP_HEADER_LENGTH+SIZEFIELD_LENGTH;
    protected final static int RSP_MAC_INDEX=RSP_ENC_INDEX+EXPECTED_ROOT_CONT.length;

    public CmpRootContRegisterActivate(){
        super(CmpMsg.MC_CMP_CMD_ROOT_CONT_REGISTER_ACTIVATE);
    }


    public CmpCommand createCommand(){
        CmpCommand command=new CmpCommand(id_);
        command.setInt(CMD_ROOTID_INDEX, ROOTID);
        command.setByteArray(CMD_ENC_INDEX, ENC);
        command.setByteArray(CMD_MAC_INDEX, MAC);
        return command;
    }    
    
    public void checkResult(CmpResponse response){
        super.checkResult(response);
        if(false==result_)return;


        byte[] rc=response.getByteArray(RSP_ENC_INDEX, EXPECTED_ROOT_CONT.length);
        if(!Arrays.equals(rc, EXPECTED_ROOT_CONT)){
            Log.i(TAG, "CmpRootContRegisterActivate: wrong root container:"+byteArrayToDisplayableString(rc));
            result_=false;
        }

        byte[] mac=response.getByteArray(RSP_MAC_INDEX, MAC.length);
        if(!Arrays.equals(mac, MAC)){
            Log.i(TAG, "CmpRootContRegisterActivate: wrong MAC:"+byteArrayToDisplayableString(mac));
            result_=false;
        }
        return;
    }
}
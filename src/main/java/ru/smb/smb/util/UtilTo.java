package ru.smb.smb.util;

/*
 * @author Alexandr.Yakubov
 **/

import ru.smb.smb.model.SmbBox;
import ru.smb.smb.to.SmbBoxTo;

public class UtilTo {

    public SmbBoxTo SmbBoxConvertSmbBoxTo(SmbBox smbBox) {
        return new SmbBoxTo(smbBox.getBox(), smbBox.getTablename());
    }

}

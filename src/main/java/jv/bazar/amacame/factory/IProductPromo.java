package jv.bazar.amacame.factory;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;

import java.util.List;

public interface IProductPromo {
    BillDetailLineReqDTO applyPromo(BillDetailLineReqDTO billDetailLines);
}

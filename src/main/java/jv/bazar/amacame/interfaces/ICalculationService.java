package jv.bazar.amacame.interfaces;

import jv.bazar.amacame.dto.req.BillReqDTO;

import java.math.BigDecimal;

public interface ICalculationService {
    public BigDecimal calculate(BillReqDTO billReqDTO);
}

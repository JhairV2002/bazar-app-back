package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillDetailLineService {

    public BigDecimal calculateTotalPriceByProduct(BillDetailLineReqDTO billDetailLineReqDTO) {
        return billDetailLineReqDTO.getProduct().getProductSalePrice().multiply(BigDecimal.valueOf(billDetailLineReqDTO.getQuantity()));
    }

    public BigDecimal calculateTotalProfitByProduct(BillDetailLineReqDTO billDetailLineReqDTO) {
        return  billDetailLineReqDTO.getProduct().getProductSalePrice().subtract(billDetailLineReqDTO.getProduct().getProductPurchasePrice()).multiply(BigDecimal.valueOf(billDetailLineReqDTO.getQuantity()));
    }

    public List<BillDetailLineReqDTO> calculatePriceAndProfitByProduct(List<BillDetailLineReqDTO> billDetailLineReqDTOList) {
        for (BillDetailLineReqDTO billDetailLineReqDTO : billDetailLineReqDTOList) {
            billDetailLineReqDTO.setTotalProfitByProduct(calculateTotalProfitByProduct(billDetailLineReqDTO));
            billDetailLineReqDTO.setTotalPriceByProduct(calculateTotalPriceByProduct(billDetailLineReqDTO));
        }
        return billDetailLineReqDTOList;
    }
}

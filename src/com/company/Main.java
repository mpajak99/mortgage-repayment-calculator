package com.company;

import com.company.model.InputData;
import com.company.model.Overpayment;
import com.company.model.RateType;
import com.company.service.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        InputData inputData = new InputData()
                .withAmount(new BigDecimal("300000"))
                .withOverpaymentSchema(Map.of(
                        5, BigDecimal.valueOf(10000),
                        6, BigDecimal.valueOf(10000),
                        7, BigDecimal.valueOf(10000),
                        8, BigDecimal.valueOf(10000),
                        9, BigDecimal.valueOf(10000),
                        10, BigDecimal.valueOf(10000),
                        11, BigDecimal.valueOf(10000),
                        12, BigDecimal.valueOf(10000),
                        13, BigDecimal.valueOf(10000),
                        14, BigDecimal.valueOf(10000)
                ))
                .withMonthsDuration(BigDecimal.valueOf(180))
                .withRateType(RateType.CONSTANT)
                .withOverpaymentReduceWay(Overpayment.REDUCE_PERIOD);


        PrintingService printingService = new PrintingServiceImpl();
        RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
                new TimePointServiceImpl(),
                new AmountsCalculationServiceImpl(
                        new ConstantAmountsCalculationServiceImpl(),
                        new DecreasingAmountsCalculationServiceImpl()
                ),
                new OverpaymentCalculationServiceImpl(),
                new ResidualCalculationServiceImpl(),
                new ReferenceCalculationServiceImpl()
        );

        MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
                printingService,
                rateCalculationService,
                SummaryServiceFactory.create()
        );
        mortgageCalculationService.calculate(inputData);
    }
}

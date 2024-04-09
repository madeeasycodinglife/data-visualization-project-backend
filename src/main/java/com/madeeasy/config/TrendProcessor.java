package com.madeeasy.config;

import com.madeeasy.entity.Trend;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TrendProcessor implements ItemProcessor<Trend, Trend> {

    @Override
    public Trend process(Trend trend) throws Exception {
        // actual business logic written here
        return trend;
    }
}

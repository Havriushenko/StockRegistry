package smida.test_task.havriushenko.stockRegestry.converters.impl;

import org.springframework.stereotype.Component;
import smida.test_task.havriushenko.stockRegestry.converters.HistoryConverter;
import smida.test_task.havriushenko.stockRegestry.dto.HistoryDto;
import smida.test_task.havriushenko.stockRegestry.models.HistoryModel;

@Component("historyConverter")
public class HistoryConverterImpl implements HistoryConverter {

    @Override
    public HistoryDto convertModelToDto(HistoryModel historyModel) {
        HistoryDto history = new HistoryDto();
        history.setPk(historyModel.getPk());
        history.setUSREOU(historyModel.getUSREOU());
        history.setFieldName(historyModel.getFieldName());
        history.setNewChange(historyModel.getNewChange());
        history.setLastChange(historyModel.getLastChange());
        history.setDateOfModified(historyModel.getDateOfModified());
        return history;
    }

    @Override
    public HistoryModel convertDtoToModel(HistoryDto history) {
        HistoryModel historyModel = new HistoryModel();
        if(history.getPk() != 0){
            historyModel.setPk(history.getPk());
        }
        historyModel.setUSREOU(history.getUSREOU());
        historyModel.setFieldName(history.getFieldName());
        historyModel.setNewChange(history.getNewChange());
        historyModel.setLastChange(history.getLastChange());
        historyModel.setDateOfModified(history.getDateOfModified());
        return historyModel;
    }
}

package management.dao;

import management.model.FeeEntity;

public interface FeeDao {
    public FeeEntity getFee(Integer bankId, Long moneyTransaction);
}

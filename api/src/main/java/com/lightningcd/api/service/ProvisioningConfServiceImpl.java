package com.lightningcd.api.service;


import com.lightningcd.api.exception.ConfigurationNotFoundException;
import com.lightningcd.api.model.ProvisioningConf;
import com.lightningcd.api.repository.ProvisioningConfRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvisioningConfServiceImpl implements ProvisioningConfService {


    private static final Logger logger = LoggerFactory.getLogger(DeployApplicationServiceImpl.class);
    private final ProvisioningConfRepository provisioningConfRepository;

    @Autowired
    public ProvisioningConfServiceImpl(
            ProvisioningConfRepository provisioningConfRepository) {
        this.provisioningConfRepository = provisioningConfRepository;
    }

    /**
     * Fetches all registered configurations.
     *
     * @return all ProvisioningConf
     */
    @Override
    public Iterable<ProvisioningConf> all() {
        return provisioningConfRepository.findAll();
    }

    /**
     * Fetches a ProvisioningConf Object
     *
     * @param id ProvisioningConf unique identifier
     * @return ProvisioningConf instance
     */
    @Override
    public ProvisioningConf get(ObjectId id) {
        ProvisioningConf provisioningConf = provisioningConfRepository.findOne(id);
        return provisioningConf;
    }

    /**
     * Fetches a ProvisionConf Object
     *
     * @param applicationName unique identifier
     * @return ProvisioningConf object
     */
    @Override
    public ProvisioningConf get(String applicationName) {
        ProvisioningConf provisioningConf = provisioningConfRepository.findByApplicationName(applicationName);
        return provisioningConf;
    }

    /**
     * Creates a new ProvisioningConfiguration and saves it.
     *
     * @param provisioningConf
     * @return applicationName
     */
    @Override
    public String create(ProvisioningConf provisioningConf) {

        ProvisioningConf provisioningConf1 = new ProvisioningConf(provisioningConf.getApplicationName(), provisioningConf.getRestEndPoint());
        provisioningConfRepository.save(provisioningConf1);
        return provisioningConf1.getApplicationName();
    }

    /**
     * Updates an existing ProvisionConfiguration.
     *
     * @param provisionConf
     * @return applicationName
     */
    @Override
    public String update(ProvisioningConf provisionConf) throws ConfigurationNotFoundException {
        ProvisioningConf provisioningConf = provisioningConfRepository.findByApplicationName(provisionConf.getApplicationName());
        provisioningConf.setApplicationName(provisionConf.getApplicationName());
        provisioningConf.setRestEndPoint(provisionConf.getRestEndPoint());
        provisioningConfRepository.save(provisioningConf);
        return provisioningConf.getApplicationName();
    }

    /**
     * Deletes an existing ProvisioningConfiguration
     *
     * @param applicationName
     */
    @Override
    public String delete(String applicationName) {
        ProvisioningConf provisioningConf = provisioningConfRepository.findByApplicationName(applicationName);
        provisioningConfRepository.delete(provisioningConf);
        return provisioningConf.getApplicationName();
    }
}

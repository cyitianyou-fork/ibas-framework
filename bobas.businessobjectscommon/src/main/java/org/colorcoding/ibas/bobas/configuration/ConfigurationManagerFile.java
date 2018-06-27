package org.colorcoding.ibas.bobas.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.MyConfiguration;

/**
 * 配置项操作类-XML
 * 
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "configuration", namespace = MyConfiguration.NAMESPACE_BOBAS_CONFIGURATION)
@XmlRootElement(name = "configuration", namespace = MyConfiguration.NAMESPACE_BOBAS_CONFIGURATION)
public class ConfigurationManagerFile extends ConfigurationManager {

	public static IConfigurationManager create(String filePath) throws FileNotFoundException, JAXBException {
		if (filePath == null || filePath.isEmpty())
			return null;
		File file = new File(filePath);
		if (!file.exists())
			return null;
		InputStream stream = new FileInputStream(file);
		JAXBContext context = JAXBContext.newInstance(ConfigurationManagerFile.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (IConfigurationManager) unmarshaller.unmarshal(stream);
	}

	public ConfigurationManagerFile() {

	}

	public ConfigurationManagerFile(String configFile) {
		this();
		this.setConfigFile(configFile);
	}

	private String configFile;

	public final String getConfigFile() {
		if (this.configFile == null || this.configFile.isEmpty()) {
			this.configFile = "app.xml";
		}
		return configFile;
	}

	public final void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	@XmlElementWrapper(name = "appSettings")
	@XmlElement(name = "add", type = ConfigurationElement.class)
	private ConfigurationElement[] getConfigurationElements() {
		return this.getElements().toArray(new ConfigurationElement[] {});
	}

	@SuppressWarnings("unused")
	private void setConfigurationElements(ConfigurationElement[] value) {
		if (value == null) {
			return;
		}
		for (ConfigurationElement item : value) {
			this.addConfigValue(item.getKey(), item.getValue());
		}
	}

	@Override
	public synchronized void save() throws Exception {
		if (this.getConfigFile() == null || this.getConfigFile().isEmpty())
			return;
		File file = new File(this.getConfigFile());
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		JAXBContext context = JAXBContext.newInstance(ConfigurationManagerFile.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(this, file);
	}

	@Override
	public synchronized void update() throws Exception {
		if (this.getConfigFile() == null || this.getConfigFile().isEmpty())
			return;
		File file = new File(this.getConfigFile());
		if (!file.exists())
			return;
		InputStream stream = new FileInputStream(file);
		JAXBContext context = JAXBContext.newInstance(ConfigurationManagerFile.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		ConfigurationManagerFile tmpManager = (ConfigurationManagerFile) unmarshaller.unmarshal(stream);
		for (IConfigurationElement item : tmpManager.getElements()) {
			this.addConfigValue(item.getKey(), item.getValue());
		}
	}

}

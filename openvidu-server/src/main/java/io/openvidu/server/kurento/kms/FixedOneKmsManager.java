/*
 * (C) Copyright 2017-2019 OpenVidu (https://openvidu.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.openvidu.server.kurento.kms;

import org.kurento.client.KurentoClient;
import org.kurento.commons.exception.KurentoException;

public class FixedOneKmsManager extends KmsManager {

	@Override
	protected void initializeKurentoClients() {
		final String kmsUri = this.openviduConfig.getKmsUris().get(0);
		KurentoClient kClient = null;
		try {
			kClient = KurentoClient.create(kmsUri, this.generateKurentoConnectionListener(kmsUri));
		} catch (KurentoException e) {
			log.error("KMS in {} is not reachable by OpenVidu Server", kmsUri);
			log.error("Shutting down OpenVidu Server");
			System.exit(1);
		}
		Kms kms = new Kms(kmsUri, kClient, loadManager);
		kms.setKurentoClientConnected(true);
		kms.setTimeOfKurentoClientConnection(System.currentTimeMillis());
		this.addKms(kms);
	}

}

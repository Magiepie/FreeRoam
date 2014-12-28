package server.net;



import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Provides access to the encoders and decoders for the 508 protocol.
 * @author Graham
 *
 */
public class CodecFactory implements ProtocolCodecFactory {
	
	/**
	 * The encoder.
	 */
	private ProtocolEncoder encoder = new RS2ProtocolEncoder();
	
	/**
	 * The decoder.
	 */
	private ProtocolDecoder decoder = new RS2LoginProtocolDecoder();
	
	
	/**
	 * Get the encoder.
	 */
	public ProtocolEncoder getEncoder() throws Exception {
		return encoder;
	}

	
	/**
	 * Get the decoder.
	 */
	public ProtocolDecoder getDecoder() throws Exception {
		return decoder;
	}

}

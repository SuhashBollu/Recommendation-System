package com.rs;

import java.io.IOException;

public interface Filter {
	public boolean satisfies(String id) throws IOException;
}

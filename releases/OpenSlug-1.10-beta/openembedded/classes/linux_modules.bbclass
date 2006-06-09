def linux_module_packages(s):
	return " ".join(map(lambda s: "kernel-module-" + s.lower().replace('_', '-').replace('@', '+'), s.split()))

# that's all
